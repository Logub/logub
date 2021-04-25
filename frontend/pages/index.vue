<template>
  <log-viewer/>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator';
import LogViewer from '~/components/log-viewer/LogViewer.vue';

@Component({
  name: 'Home',
  components: { LogViewer }
})
export default class Home extends Vue {
}
</script>

<style scoped>

</style>
