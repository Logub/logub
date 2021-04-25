import { Module, VuexAction, VuexModule, VuexMutation } from 'nuxt-property-decorator';
import { LogLevel } from '~/models/LogLevel';
import { LogubLog } from '~/models/LogubLog';

const logTemplate: (v: number) => LogubLog = (v: number) => ({
  id: `azbve-obivoz-zabioz-abizoa-${v}`,
  index: "principal",
  timestamp: Date.now() + v,
  level: v % 4 === 0 ? LogLevel.INFO : (v % 4 === 1 ? LogLevel.WARN : (v % 4 === 2 ? LogLevel.ERROR : LogLevel.DEBUG)),
  service: "logub-backend-api",
  businessProperties: {},
  tags: {},
  message: "API Started - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ut leo nec magna tristique pulvinar. Sed et sagittis nisi. Proin posuere mi in tortor viverra, a suscipit est molestie. Nunc vulputate varius urna id venenatis. In facilisis dolor vel cursus malesuada. Maecenas ut turpis accumsan, suscipit dolor a, maximus tortor. Aenean id lacinia metus. Ut bibendum maximus congue. Quisque vulputate massa tortor, in consequat leo elementum quis. Morbi mauris tortor, blandit in sem cursus, suscipit sollicitudin turpis."
});

const logs: LogubLog[] = [...Array(200).keys()].map(i => logTemplate(i));

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
  updateLogs(options: { size: number }): void {
    this.setLoading(true);
    const filteredLogs = logs.slice(0, options.size);

    setTimeout(() => {
      this.setLogs(filteredLogs);
      this.setLoading(false);
    }, 2000);
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
