import { Store } from 'vuex';
import { getModule } from 'nuxt-property-decorator';
import Logs from '~/store/logs';
import { NuxtAxiosInstance } from '@nuxtjs/axios';
import Schema from "~/store/schema";

let logs: Logs;
let schema: Schema;
function initialiseStores(store: Store<any>): void {
  logs = getModule(Logs, store);
  schema = getModule(Schema, store);
}

export { initialiseStores, logs, schema };

let $axios: NuxtAxiosInstance;

export function initializeAxios(axiosInstance: NuxtAxiosInstance) {
  $axios = axiosInstance;
}

export { $axios };
