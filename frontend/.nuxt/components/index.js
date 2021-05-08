import { wrapFunctional } from './utils'

export { default as NavigationLogubDrawer } from '../../components/navigation/LogubDrawer.vue'
export { default as LogViewer } from '../../components/log-viewer/LogViewer.vue'
export { default as LogViewerSearchBar } from '../../components/log-viewer/SearchBar.vue'

export const LazyNavigationLogubDrawer = import('../../components/navigation/LogubDrawer.vue' /* webpackChunkName: "components/navigation-logub-drawer" */).then(c => wrapFunctional(c.default || c))
export const LazyLogViewer = import('../../components/log-viewer/LogViewer.vue' /* webpackChunkName: "components/log-viewer" */).then(c => wrapFunctional(c.default || c))
export const LazyLogViewerSearchBar = import('../../components/log-viewer/SearchBar.vue' /* webpackChunkName: "components/log-viewer-search-bar" */).then(c => wrapFunctional(c.default || c))
