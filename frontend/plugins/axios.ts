import { Context, Plugin } from '@nuxt/types';
import { initializeAxios } from '~/utils/store-accessor';

const accessorAxios: Plugin = (ctx: Context) => {
  initializeAxios((ctx as any).$axios);
};

export default accessorAxios;
