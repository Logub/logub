import { Store } from 'vuex';
import { getModule } from 'nuxt-property-decorator';
import Logs from '~/store/logs';
import { NuxtAxiosInstance } from '@nuxtjs/axios';

let logs: Logs;

function initialiseStores(store: Store<any>): void {
  logs = getModule(Logs, store);
}

export { initialiseStores, logs };

let $axios: NuxtAxiosInstance;

export function initializeAxios(axiosInstance: NuxtAxiosInstance) {
  $axios = axiosInstance;
}

export { $axios };
