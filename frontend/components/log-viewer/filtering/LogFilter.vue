<template>
  <v-expansion-panels tile flat v-if="logs.length > 0">
    <v-expansion-panel>
      <v-expansion-panel-header class="font-weight-bold">
        {{ this.field }}
      </v-expansion-panel-header>
      <v-expansion-panel-content>
        <v-checkbox
          class="value ma-0"
          v-for="log in logs"
          :key="log.log.id"
          :label="log.value"
          :input-value="isFieldInMatchingQuery(log.value)"
          @change="check => onCheckboxChanged(check, log)"
          dense
        />
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>
<script lang="ts">
import Vue from 'vue';
import { Component, Emit, Prop } from 'nuxt-property-decorator';
import { FieldType, LogFieldFilter, LogubLog } from '~/models/LogubLog';
import { logs, search } from '~/utils/store-accessor';
import { SystemProperties } from '~/models/SystemProperties';
import { FieldSearchDto, FieldTypeDto } from '~/models/dto/FieldSearchDto';

@Component({
  name: "LogFilter"
})
export default class LogFilter extends Vue {
  @Prop({ required: true })
  private readonly field!: string;

  @Prop({ required: true })
  private readonly type!: FieldType;

  get logs(): LogFieldFilter[] {
    let res: LogFieldFilter[] = [];
    switch (this.type) {
      case FieldType.BASIC:
        res = logs.logs
          .filter(log => this.isFieldValidBasic(this.field, log) && log[this.field])
          .map(log => ({ log, value: this.isFieldValidBasic(this.field, log) ? log[this.field] as string : 'N/A' }));
        break;
      case FieldType.SYSTEM:
        res = logs.logs
          .filter(log => this.isFieldValidSystem(this.field, log) && log.tags[this.field])
          .map(log => ({
            log,
            value: this.isFieldValidSystem(this.field, log) ? log.tags[this.field] as string : 'N/A'
          }));
        break;
      case FieldType.BUSINESS:
        res = logs.logs
          .filter(log => log.businessProperties[this.field])
          .map(log => ({ log, value: log.businessProperties[this.field] as string }));
        break;
    }

    console.log("Logs Filter", res);
    const isEqual = (a: LogFieldFilter, b: LogFieldFilter) => a.value === b.value;
    res = res.filter((value, index, array) => !array.filter((v, i) => isEqual(value, v) && i < index).length);
    return res;
  }

  get isFieldInMatchingQuery() {
    return (value: string) => !!search.matchingQuery.find(m => m.name === this.field && m.values.includes(value));
  }

  isFieldValidBasic(field: string, log: LogubLog): field is keyof LogubLog {
    return field in log;
  }

  isFieldValidSystem(field: string, log: LogubLog): field is keyof SystemProperties {
    return field in log.tags;
  }

  onCheckboxChanged(checked: boolean, selectedLog: LogFieldFilter): void {
    if (checked) {
      search.addToQuery({
        name: this.field,
        type: FieldTypeDto.Tag,
        values: [selectedLog.value],
        negation: !checked,
      });
      this.onSearchChanged();
    } else {
      const query = search.matchingQuery.findIndex(m => m.name === this.field && m.values.includes(selectedLog.value));
      if (query !== -1) {
        search.removeFromQuery(query);
        this.onSearchChanged();
      }
    }
  }

  @Emit()
  onSearchChanged(): Array<FieldSearchDto> {
    console.log("SEARCH CHANGED from logFilter");
    return search.matchingQuery;
  }
}
</script>
<style scoped>
.title {
  width: 100%;
}

.value {
  width: 100%;
}
</style>
