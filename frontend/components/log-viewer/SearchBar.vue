<template>
  <div>
    <v-row id="search-bar" align="center" justify="space-between">
      <v-col cols="8">
        <v-text-field
          :value="searchQuery"
          @input="onSearchQueryUpdated"
          @keydown.enter="searchTextUpdate()"
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
            :key="index"
            :close="true"
            :color="typeColor(tag.type)"
            style="color: white!important;"
            @click:close="onDeleteChips(index)"
          >
            {{
              formatChip(tag)
            }}
          </v-chip>
        </v-chip-group>
      </v-col>
    </v-row>
  </div>
</template>
<script lang="ts">
import Vue from 'vue';
import { Component, Emit, Watch } from 'nuxt-property-decorator';
import moment from 'moment';
import { defaultLogDateFilter, LogDateFilter } from '~/models/LogDateFilter';
import { FieldSearchDto, FieldTypeDto } from "~/models/dto/FieldSearchDto";
import { search } from '~/utils/store-accessor';

@Component({
  name: "SearchBar"
})
export default class SearchBar extends Vue {

  private readonly items: LogDateFilter[] = [
    defaultLogDateFilter,
    {
      text: 'Last 30 minutes',
      beginAt: () => moment().toDate(),
      endAt: () => moment().subtract(30, "minutes").toDate()
    },
    {
      text: 'Last 1 hour',
      beginAt: () => moment().toDate(),
      endAt: () => moment().subtract(1, "hour").toDate()
    },
    {
      text: 'Last 2 hours',
      beginAt: () => moment().toDate(),
      endAt: () => moment().subtract(2, "hours").toDate()
    },
    {
      text: 'Last 4 hours',
      beginAt: () => moment().toDate(),
      endAt: () => moment().subtract(4, "hours").toDate()
    },
    {
      text: 'Last 1 day',
      beginAt: () => moment().toDate(),
      endAt: () => moment().subtract(1, "day").toDate()
    },
    {
      text: 'Last 1 week',
      beginAt: () => moment().toDate(),
      endAt: () => moment().subtract(1, "week").toDate()
    }
  ];

  private selectedDate: LogDateFilter = this.items[0];

  get searchQuery() {
    return search.searchQuery;
  }

  get matchingQuery() {
    return search.matchingQuery;
  }

  @Watch('selectedDate')
  onSelectedDateChanged(): void {
    this.onTimeChanged();
  }

  onSearchQueryUpdated(value: string) {
    search.setSearch(value);
  }

  //TODO fulltext search bug "test env:dev test2" will search => "test test2" and not "test" "test2"
  searchTextUpdate() {
    search.updateSearch();
    this.onSearchChanged();
  }

  @Emit()
  onSearchChanged(): Array<FieldSearchDto> {
    return this.matchingQuery;
  }

  @Emit()
  onTimeChanged(): LogDateFilter {
    return this.selectedDate;
  }

  onDeleteChips(index: number) {
    search.removeFromQuery(index);
    this.onSearchChanged();
  }

  formatChip(field: FieldSearchDto): string {
    const multiple = field.values.length > 1;
    const negation = field.negation ? '- ' : '';
    switch (field.type) {
      case FieldTypeDto.FullText:
        return `${negation}message:${multiple ? '(' : ''}${field.values.join(" AND ")}${multiple ? ')' : ''}`;
      case FieldTypeDto.Tag:
        return `${negation}${field.name}:${multiple ? '(' : ''}${field.values.join(" OR ")}${multiple ? ')' : ''}`;
      default:
        throw new Error("unsupported");
    }
  }

  typeColor(type: FieldTypeDto): string {
    switch (type) {
      case FieldTypeDto.FullText:
        return "#fb8500";
      case FieldTypeDto.Numeric:
        return "#023047";
      case FieldTypeDto.Tag:
        return "#219ebc";
      case FieldTypeDto.Geo:
        return "#2a9d8f";
    }
  }
}
</script>
<style scoped>
#search-bar >>> .v-input__prepend-inner {
  margin-right: 7px;
}
</style>
