<template>
  <v-container fluid>
    <log-filter class="mb-1" v-for="(basicProp, i) in basicProperties" :key="`basic-${i}`" :field="basicProp"
                :type="basicType"/>

    <log-filter class="mb-1" v-for="(systemProp, i) in systemProperties" :key="`system-${i}`" :field="systemProp"
                :type="systemType"/>

    <log-filter class="mb-1" v-for="(businessProp, i) in businessProperties" :key="`business-${i}`"
                :field="businessProp"
                :type="businessType"/>
  </v-container>
</template>
<script lang="ts">
import Vue from 'vue';
import { Component } from 'nuxt-property-decorator';
import { schema } from '~/utils/store-accessor';
import LogFilter from '~/components/log-viewer/filtering/LogFilter.vue';
import { FieldType } from '~/models/LogubLog';

@Component({
  name: "Filters",
  components: { LogFilter }
})
export default class Filters extends Vue {

  get basicProperties(): string[] {
    return schema.basicProperties.filter(p => !['message'].includes(p));
  }

  get systemProperties(): string[] {
    console.log('SYSTEM PROPS', schema.systemProperties);
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
}
</script>
<style scoped>
</style>
