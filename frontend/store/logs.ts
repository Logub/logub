import { Module, VuexAction, VuexModule, VuexMutation } from 'nuxt-property-decorator';
import { LogubLog } from '~/models/LogubLog';
import { Api } from '~/utils/api';
import { Mapper } from '~/utils/mapper';
import { FieldSearchDto, FieldTypeDto } from '~/models/dto/FieldSearchDto';
import { sleep } from '~/utils/helpers';
import { schema } from "~/utils/store-accessor";
import { SearchLogsDto, SortLogsDto } from "~/models/dto/SearchLogsDto";

@Module({
  name: 'logs',
  stateFactory: true,
  namespaced: true,
})
export default class Logs extends VuexModule {
  private _loading: boolean = true;

  get loading(): boolean {
    return this._loading;
  }

  private _logs: LogubLog[] = [];

  get logs(): LogubLog[] {
    return this._logs;
  }

  @VuexAction
  async updateLogs(options: { properties?: Array<FieldSearchDto>, sort?: SortLogsDto, size: number, beginAtInMs: number, endAtInMs: number }): Promise<void> {
    if (schema.schema.length === 0) {
      await schema.updateSchema();
      console.log('schema updated');
    }
    const business: Array<string> = schema.businessProperties;
    const system: Array<string> = schema.systemProperties;  //sche
    const basic: Set<string> = schema.basicProperties;  //scma.getSystemProperties();
    //Since business and system properties are defined in schema, we don't care about split them before here
    this.setLoading(true);
    try {
      const { properties, size, beginAtInMs, endAtInMs, sort } = options;
      const texts = properties?.filter(v => v.type === FieldTypeDto.FullText);
      const businessProperties = properties?.filter(v => v.type === FieldTypeDto.Tag && business.includes(v.name!));
      const systemProperties = properties?.filter(v => v.type === FieldTypeDto.Tag && system.includes(v.name!));
      const basicProperties = properties?.filter(v => v.type === FieldTypeDto.Tag && basic.has(v.name!));
      const levels = properties?.filter(v => v.type === FieldTypeDto.Tag && v.name!.toLowerCase() === 'level');
      const search: SearchLogsDto = {
        texts: texts ? texts : [],
        businessProperties: businessProperties ? businessProperties : [],
        systemProperties: systemProperties ? systemProperties : [],
        basicProperties: basicProperties ? basicProperties : [],
        levels: levels ? levels : [],
        sort: sort ? sort : {
          field: 'timestamp',
          order: 'DESC'
        },
        endAt: Math.round(beginAtInMs / 1000),
        beginAt: Math.round(endAtInMs / 1000),
        limit: size,
        offset: 0
      };
      console.log("search : ", search);
      const logsPromise = Api.searchLogs(search);

      const all = await Promise.all([logsPromise, sleep(500)]);
      const logs = all[0];
      this.setLogs(logs.map(log => Mapper.toDomain(log)));
    } catch (err) {
      console.error(err);
    } finally {
      this.setLoading(false);
    }
  }

  @VuexMutation
  private setLoading(isLoading: boolean): void {
    this._loading = isLoading;
  }

  @VuexMutation
  private setLogs(newLogs: LogubLog[]): void {
    this._logs = newLogs;
  }
}
