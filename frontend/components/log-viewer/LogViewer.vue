<template>
  <v-container class="logs-container">
    <v-data-table
      dense
      calculate-widths
      :headers="headers"
      :items="logsData"
      item-key="id"
      class="rounded-0 logs-table"
    >
      <template v-slot:item.timestamp="{ item, value }">
        <span class="level-item" :style="logLevelColor(item.level)"/>
        <span>{{ formatDate(value) }}</span>
      </template>
    </v-data-table>
  </v-container>
</template>
<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';
import { logs } from '~/utils/store-accessor';
import { Moment } from 'moment';
import moment from 'moment/moment';
import { LogLevel, logLevelColor } from '~/models/LogLevel';

@Component({
  name: "LogViewer"
})
export default class LogViewer extends Vue {
  private headers: DataTableHeader[] = [
    {
      text: 'DATE',
      align: 'start',
      value: 'timestamp',
      divider: true,
      width: '210px'
    },
    {
      text: 'SERVICE',
      align: 'start',
      value: 'service',
      divider: true,
      width: '260px'
    },
    {
      text: 'MESSAGE',
      align: 'start',
      value: 'message',
      divider: true
    },
  ];

  get logsData() {
    return logs.logs;
  }

  get logLevelColor() {
    return (level: LogLevel) => `border-color: ${logLevelColor(level)}`;
  }

  private formatDate(dateNumber: number): string {
    const date: Moment = moment(dateNumber);
    return date.format('MMM DD HH:mm:ss.SSS');
  }
}
</script>
<style scoped>
.logs-container {
  font-family: monospace;
}

.logs-table {
  border: thin solid #DDD;
  border-right-width: 0;
}

.logs-table >>> td {
  padding: 0 8px !important;
  font-size: 12px !important;
  white-space: nowrap;
}

.level-item {
  border-left: 3px solid white;
  margin-right: 10px;
}
</style>
