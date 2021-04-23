<template>
  <v-container>
    <v-data-table
      dense
      :headers="headers"
      :items="logsData"
      item-key="id"
    />
  </v-container>
</template>
<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';
import { logs } from '~/utils/store-accessor';

@Component({
  name: "LogViewer"
})
export default class LogViewer extends Vue {
  private headers: DataTableHeader[] = [
    {
      text: 'Date',
      align: 'start',
      value: 'timestamp'
    },
    {
      text: 'Level',
      align: 'start',
      value: 'level'
    },
    {
      text: 'Message',
      align: 'start',
      value: 'message'
    },
  ];

  get logsData() {
    return logs.logs;
  }


}
</script>
<style scoped>
</style>
