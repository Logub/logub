<template>
  <v-container fluid>
    <h3 class="text-center">Filtering</h3>
    <v-divider class="mx-15 mt-1 mb-6" />

    <v-expansion-panels tile flat class="mb-2">
      <v-expansion-panel class="color-panel">
        <v-expansion-panel-header class="font-weight-bold">
          Basic Properties
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <log-filter
            class="mb-1"
            v-for="(basicProp, i) in basicProperties"
            :key="`basic-${i}`"
            :field="basicProp"
            :type="basicType"
            @on-search-changed="() => onSearchChanged()"
          />
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>

    <v-expansion-panels tile flat class="mb-2">
      <v-expansion-panel class="color-panel">
        <v-expansion-panel-header class="font-weight-bold">
          System Properties
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <log-filter
            class="mb-1"
            v-for="(systemProp, i) in systemProperties"
            :key="`system-${i}`"
            :field="systemProp"
            :type="systemType"
            @on-search-changed="() => onSearchChanged()"
          />
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>

    <v-expansion-panels tile flat class="mb-2">
      <v-expansion-panel class="color-panel">
        <v-expansion-panel-header class="font-weight-bold">
          Business Properties
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <log-filter
            class="mb-1"
            v-for="(businessProp, i) in businessProperties"
            :key="`business-${i}`"
            :field="businessProp"
            :type="businessType"
            @on-search-changed="() => onSearchChanged()"
          />
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </v-container>
</template>
<script lang="ts">
import Vue from "vue";
import { Component, Emit } from "nuxt-property-decorator";
import { schema, search } from "~/utils/store-accessor";
import LogFilter from "~/components/log-viewer/filtering/LogFilter.vue";
import { FieldType } from "~/models/LogubLog";
import { FieldSearchDto } from "~/models/dto/FieldSearchDto";

@Component({
  name: "Filters",
  components: { LogFilter },
})
export default class Filters extends Vue {
  get basicProperties(): string[] {
    return schema.basicProperties.filter((p) => !["message"].includes(p));
  }

  get systemProperties(): string[] {
    console.log("SYSTEM PROPS", schema.systemProperties);
    return schema.systemProperties;
  }

  get businessProperties(): string[] {
    return schema.businessProperties;
  }

  get basicType(): FieldType {
    return FieldType.BASIC;
  }

  get systemType(): FieldType {
    return FieldType.SYSTEM;
  }

  get businessType(): FieldType {
    return FieldType.BUSINESS;
  }

  @Emit()
  onSearchChanged(): Array<FieldSearchDto> {
    console.log("SEARCH CHANGED from Filters");
    return search.matchingQuery;
  }
}
</script>
<style scoped>
.color-panel {
  border: 1px solid #ddd;
}
</style>
