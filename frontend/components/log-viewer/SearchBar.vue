<template>
  <v-row id="search-bar" align="center" justify="space-between">
    <v-col cols="8">
      <v-text-field
        label="Search in your logs"
        flat
        dense
      />
    </v-col>
    <v-col cols="4">
      <v-select
        v-model="selectedDate"
        :items="items"
        return-object
        item-text="text"
        prepend-inner-icon="mdi-update"
        dense
        outlined
      />
    </v-col>
  </v-row>
</template>
<script lang="ts">
import Vue from 'vue';
import { Component } from 'nuxt-property-decorator';
import moment from 'moment';
import { LogDateFilter } from '~/models/LogDateFilter';

@Component({
  name: "SearchBar"
})
export default class SearchBar extends Vue {
  private readonly items: LogDateFilter[] = [
    {
      text: 'Last 15 minutes',
      beginAt: moment().toDate(),
      endAt: moment().subtract(15, "minutes").toDate()
    },
    {
      text: 'Last 30 minutes',
      beginAt: moment().toDate(),
      endAt: moment().subtract(30, "minutes").toDate()
    },
    {
      text: 'Last 1 hour',
      beginAt: moment().toDate(),
      endAt: moment().subtract(1, "hour").toDate()
    },
    {
      text: 'Last 2 hours',
      beginAt: moment().toDate(),
      endAt: moment().subtract(2, "hours").toDate()
    },
    {
      text: 'Last 4 hours',
      beginAt: moment().toDate(),
      endAt: moment().subtract(4, "hours").toDate()
    },
    {
      text: 'Last 1 day',
      beginAt: moment().toDate(),
      endAt: moment().subtract(1, "day").toDate()
    },
    {
      text: 'Last 1 week',
      beginAt: moment().toDate(),
      endAt: moment().subtract(1, "week").toDate()
    }
  ];

  private selectedDate: LogDateFilter = this.items[0];
}
</script>
<style scoped>
#search-bar >>> .v-input__prepend-inner {
  margin-right: 7px;
}
</style>
