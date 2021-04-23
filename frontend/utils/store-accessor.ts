import { Store } from 'vuex';
import { getModule } from 'nuxt-property-decorator';
import Logs from '~/store/logs';

let logs: Logs;

function initialiseStores(store: Store<any>): void {
  logs = getModule(Logs, store);
}

export { initialiseStores, logs };
