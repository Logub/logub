import Vue from 'vue'
import { wrapFunctional } from './utils'

const components = {
  NavigationLogubDrawer: () => import('../../components/navigation/LogubDrawer.vue' /* webpackChunkName: "components/navigation-logub-drawer" */).then(c => wrapFunctional(c.default || c)),
  LogViewer: () => import('../../components/log-viewer/LogViewer.vue' /* webpackChunkName: "components/log-viewer" */).then(c => wrapFunctional(c.default || c)),
  LogViewerSearchBar: () => import('../../components/log-viewer/SearchBar.vue' /* webpackChunkName: "components/log-viewer-search-bar" */).then(c => wrapFunctional(c.default || c))
}

for (const name in components) {
  Vue.component(name, components[name])
  Vue.component('Lazy' + name, components[name])
}
