<template>
  <div>
    <v-row id="search-bar" align="center" justify="space-between">
      <v-col cols="8">
        <v-text-field
          v-model="searchQuery"
          @keydown.enter="onSearchChanged()"
          label="Search in your logs"
          prepend-inner-icon="mdi-magnify"
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
    <v-row align="center" justify="start">
      <v-col cols="12">
        <v-chip-group
          mandatory
          active-class="primary--text"
        >
          <v-chip
            v-for="(tag,index) in matchingQuery"
            :key="tag"
            :close="true"
            @click:close="onDeleteChips(tag)"
          >
            {{ tag }}
          </v-chip>
        </v-chip-group>
      </v-col>
    </v-row>
  </div>
</template>
<script lang="ts">
import Vue from 'vue';
import {Component, Emit, Watch} from 'nuxt-property-decorator';
import moment from 'moment';
import {defaultLogDateFilter, LogDateFilter} from '~/models/LogDateFilter';
import {schema} from '~/utils/store-accessor';

@Component({
  name: "SearchBar"
})
export default class SearchBar extends Vue {

  private readonly items: LogDateFilter[] = [
    defaultLogDateFilter,
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

  private searchQuery: string = '';
  private selectedDate: LogDateFilter = this.items[0];
  private matchingQuery: Set<String> = new Set<String>();


  @Watch('selectedDate')
  onSelectedDateChanged(): void {
    this.onTimeChanged();
  }

  @Emit()
  //TODO fulltext search bug "test env:dev test2" will search => "test test2" and not "test" "test2"
  onSearchChanged(): string {
    console.log(this.getSchema())
    const regex = /\S+:\S+/gm;
    const matchArray: string[] = [];
    let m;
    const textInQuoteRegex = /(["'])(?:\\.|[^\\])*?\1/gm
    while ((m = textInQuoteRegex.exec(this.searchQuery)) !== null) {
      // This is necessary to avoid infinite loops with zero-width matches
      if (m.index === regex.lastIndex) {
        regex.lastIndex++;
      }
      // The result can be accessed through the `m`-variable.
      console.log(m)
      m.forEach((match, groupIndex) => {
        if (groupIndex == 0) {
          matchArray.push(match);
        }
      });
    }

    let str = this.searchQuery.replace(textInQuoteRegex, '');
    while ((m = regex.exec(str)) !== null) {
      // This is necessary to avoid infinite loops with zero-width matches
      if (m.index === regex.lastIndex) {
        regex.lastIndex++;
      }

      // The result can be accessed through the `m`-variable.
      m.forEach((match, groupIndex) => {
        matchArray.push(match)
      });
    }
    matchArray.forEach(v => {
      this.matchingQuery.add(v);
    });

    const value = this.searchQuery.replace(regex, '').trim();

    if (!this.isBlank(value)) {
      this.matchingQuery.add(value);
    }

    this.searchQuery = "";

    return "NOT MATCHING NEW MODEL";
  }

  mounted() {
    this.updateSchema();
  }

  public updateSchema() {
    return schema.updateSchema();
  }

  public getSchema() {
    return schema.schema;
  }



  @Emit()
  onTimeChanged(): LogDateFilter {
    return this.selectedDate;
  }

  onDeleteChips(index: string) {
    console.log(index);
    this.matchingQuery.delete(index);
    this.$forceUpdate();
  }

  isBlank(str: string) {
    return (!str || /^\s*$/.test(str));
  }
}
</script>
<style scoped>
#search-bar >>> .v-input__prepend-inner {
  margin-right: 7px;
}
</style>
