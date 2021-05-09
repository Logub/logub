<template>
  <v-container fluid class="logs-container">
    <search-bar
      @on-time-changed="onTimeChanged"
      @on-search-changed="onSearchChanged"
    />
    <v-data-table
      dense
      calculate-widths
      :headers="headers"
      :items="logsData"
      hide-default-footer
      :items-per-page="pageSize"
      no-data-text="No logs found here"
      item-key="id"
      class="rounded-0 logs-table mb-15"
      @click:row="onRowClicked"
    >
      <template v-slot:footer="{ options }">
        <v-progress-linear
          v-if="isLoading"
          color="primary"
          indeterminate
        />
        <div v-if="isLoading" class="text-center py-2 mx-auto caption">Fetching more logs...</div>
        <div v-else class="text-center py-2 mx-auto caption border-top">No more logs</div>
      </template>
      <template v-slot:item.timestamp="{ item, value, index }">
        <span class="level-item" :style="logLevelColor(item.level)"/>
        <span v-intersect="{
            handler: onIntersect,
            options: {
              threshold: [0, 0.5, 1.0]
            }
          }">
          <span style="display: none">{{ index }}</span>
          {{ format(value) }}
        </span>
      </template>
    </v-data-table>
    <log-detail v-if="selectedLog" v-model="dialogOpen" :log="selectedLog"/>
  </v-container>
</template>
<script lang="ts">
import Vue from 'vue';
import { Component } from 'vue-property-decorator';
import { DataTableHeader } from 'vuetify';
import { logs } from '~/utils/store-accessor';
import { LogLevel, logLevelColor } from '~/models/LogLevel';
import SearchBar from '~/components/log-viewer/SearchBar.vue';
import { defaultLogDateFilter, LogDateFilter } from '~/models/LogDateFilter';
import { formatDate } from '~/utils/helpers';
import { LogubLog } from '~/models/LogubLog';
import {FieldSearchDto, FieldTypeDto} from "~/models/dto/FieldSearchDto";

@Component({
  name: "LogViewer",
  components: { SearchBar }
})
export default class LogViewer extends Vue {
  private pageSize: number = Infinity;

  private currentPageSize: number = 50;
  private dialogOpen: boolean = false;
  private selectedLog: LogubLog = null;

  private currentDateRange: LogDateFilter = defaultLogDateFilter;
  private currentSearch: Array<FieldSearchDto> = [];

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
      divider: true,
      sortable: false
    },
  ];

  get isLoading() {
    return logs.loading;
  }

  get logsData() {
    return logs.logs;
  }

  get dataLength() {
    return 1000;
  }

  get logLevelColor() {
    return (level: LogLevel) => `border-color: ${logLevelColor(level)}`;
  }

  mounted() {
    this.fetchMoreLogs();
  }

  onIntersect(entries: IntersectionObserverEntry[], observer: IntersectionObserver) {
    // If the last item intersects and theres more pages to load, emit 'loadMoreItems'
    if (entries[0].isIntersecting
      && ((entries[0].target.firstChild?.firstChild?.nodeValue ?? "") === ("" + (this.currentPageSize - 1)))
      && this.currentPageSize < this.dataLength
    ) {
      console.log("YES", entries);
      this.currentPageSize += 50;
      this.fetchMoreLogs();
    }
  }

  onTimeChanged(newTime: LogDateFilter): void {
    this.currentDateRange = newTime;
    this.fetchMoreLogs();
  }

  onSearchChanged(newSearch: Array<FieldSearchDto>): void {
    this.currentSearch = newSearch;
    this.fetchMoreLogs();
  }

  onRowClicked(item: LogubLog): void {
    this.selectedLog = item;
    this.dialogOpen = true;
  }

  private fetchMoreLogs(): void {
    logs.updateLogs({
      properties: this.currentSearch,
      size: this.currentPageSize,
      beginAtInMs: this.currentDateRange.beginAt.getTime(),
      endAtInMs: this.currentDateRange.endAt.getTime()
    });
  }

  private format(dateNumber: number): string {
    return formatDate(dateNumber);
  }
}
</script>
<style scoped>
.border-top {
  border-top: thin solid #DDD;
}

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
  overflow: hidden;
  max-width: 100px;
  text-overflow: ellipsis;
}

.level-item {
  border-left: 3px solid white;
  margin-right: 10px;
}
</style>
