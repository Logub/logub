import { Store } from 'vuex';
import { getModule } from 'nuxt-property-decorator';
import Logs from '~/store/logs';
import { NuxtAxiosInstance } from '@nuxtjs/axios';
import Schema from "~/store/schema";
import Demo from "~/store/demo";
import Search from "~/store/search";

let logs: Logs;
let schema: Schema;
let demo: Demo;
let search: Search;
function initialiseStores(store: Store<any>): void {
  logs = getModule(Logs, store);
  schema = getModule(Schema, store);
  demo = getModule(Demo, store);
  search = getModule(Search, store);
}

export { initialiseStores, logs, schema, demo, search };

let $axios: NuxtAxiosInstance;

export function initializeAxios(axiosInstance: NuxtAxiosInstance) {
  $axios = axiosInstance;
}

export { $axios };
