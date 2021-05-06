import { Module, VuexAction, VuexModule, VuexMutation } from 'nuxt-property-decorator';
import { LogubLog } from '~/models/LogubLog';
import { Api } from '~/utils/api';
import { Mapper } from '~/utils/mapper';

@Module({
  name: 'schema',
  stateFactory: true,
  namespaced: true,
})
export default class Schema extends VuexModule {
  private _loading: boolean = true;

  get loading(): boolean {
    return this._loading;
  }

  private _schema: string[] = [];

  get schema(): string[] {
    return this._schema;
  }

  @VuexAction
  async updateSchema(): Promise<void> {
    this.setLoading(true);
    try {
      const schema = await Api.getSchema();

      this.setSchema(schema);
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
  private setSchema(newLogs: string[]): void {
    this._schema = newLogs;
  }
}
