import { Module, VuexAction, VuexModule, VuexMutation } from 'nuxt-property-decorator';
import { LogLevel } from '~/models/LogLevel';
import { LogubLog } from '~/models/LogubLog';

@Module({
  name: 'logs',
  stateFactory: true,
  namespaced: true,
})
export default class Logs extends VuexModule {
  private _logs: LogubLog[] = [];

  get logs(): LogubLog[] {
    return this._logs;
  }

  @VuexAction
  updateLogs() {
    const logTemplate: (v: number) => LogubLog = (v: number) => ({
      id: `azbve-obivoz-zabioz-abizoa-${v}`,
      index: "principal",
      timestamp: Date.now() + v,
      level: LogLevel.INFO,
      service: "logub-backend-api",
      businessProperties: {},
      tags: {},
      message: "API Started - Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse ut leo nec magna tristique pulvinar. Sed et sagittis nisi. Proin posuere mi in tortor viverra, a suscipit est molestie. Nunc vulputate varius urna id venenatis. In facilisis dolor vel cursus malesuada. Maecenas ut turpis accumsan, suscipit dolor a, maximus tortor. Aenean id lacinia metus. Ut bibendum maximus congue. Quisque vulputate massa tortor, in consequat leo elementum quis. Morbi mauris tortor, blandit in sem cursus, suscipit sollicitudin turpis."
    });

    const logs: LogubLog[] = [...Array(100).keys()].map(i => logTemplate(i));
    this.setLogs(logs);
  }

  @VuexMutation
  private setLogs(newLogs: LogubLog[]) {
    this._logs = newLogs;
  }
}
