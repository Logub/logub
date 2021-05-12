import { Module, VuexAction, VuexModule, VuexMutation } from 'nuxt-property-decorator';
import { basicProperties } from '~/models/LogubLog';
import { Api } from '~/utils/api';

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

  private _basicProperties: Set<string> = basicProperties();

  get basicProperties(): string[] {
    console.log(this._basicProperties);
    return [...this._basicProperties];
  }

  get businessProperties(): string[] {
    return this.schema.filter(v => v.startsWith("businessProperties."))
      .map(v => v.replace('businessProperties.', ''));
  }

  get systemProperties(): string[] {
    return this.schema.filter(v => v.startsWith("systemProperties."))
      .map(v => v.replace('systemProperties.', ''));
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

  @VuexAction
  async addFieldToSchema(businessProperties: string): Promise<void> {
    try {
      await Api.addField(businessProperties);
      const schema = await Api.getSchema();

      this.setSchema(schema);
    } catch (err) {
      console.error(err);
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
