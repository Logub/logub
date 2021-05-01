import { Module, VuexAction, VuexModule, VuexMutation } from 'nuxt-property-decorator';
import { LogubLog } from '~/models/LogubLog';
import { Api } from '~/utils/api';
import { Mapper } from '~/utils/mapper';

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
  async updateLogs(options: { search?: string; size: number, beginAtInMs: number, endAtInMs: number }): Promise<void> {
    this.setLoading(true);
    try {
      const { search, size, beginAtInMs, endAtInMs } = options;
      const logs = await Api.searchLogs({
        text: search,
        businessProperties: {},
        tags: {},
        sort: {
          field: 'event.timestamp',
          order: 'DESC'
        },
        endAt: Math.round(beginAtInMs / 1000),
        beginAt: Math.round(endAtInMs / 1000),
        limit: size,
        offset: 0
      });

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
