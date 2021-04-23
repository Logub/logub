<template>
  <log-viewer/>
</template>

<script lang="ts">
import { Component, Vue } from 'nuxt-property-decorator';
import LogViewer from '~/components/log-viewer/LogViewer.vue';
import { logs } from '~/utils/store-accessor';

@Component({
  name: 'Home',
  components: { LogViewer }
})
export default class Home extends Vue {
  mounted() {
    logs.updateLogs();
  }
}
</script>

<style scoped>

</style>
