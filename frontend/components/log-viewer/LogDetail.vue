<template>
  <v-dialog v-model="dialog" max-width="700px" scrollable>
    <v-card height="800px" class="card-log" :style="logLevelColor(log.level)">
      <v-card-title>
        {{ formattedDate }}
      </v-card-title>
      <v-divider></v-divider>
      <v-card-text style="height: 300px">
        <p class="default-prop mt-3"><b>id:</b> {{ log.id }}</p>
        <p class="default-prop"><b>index:</b> {{ log.index }}</p>
        <p class="default-prop"><b>level:</b> {{ log.level }}</p>
        <p class="default-prop"><b>service:</b> {{ log.service || "N/A" }}</p>
        <p class="default-prop"><b>logger:</b> {{ log.logger || "N/A" }}</p>
        <p class="default-prop"><b>thread:</b> {{ log.thread || "N/A" }}</p>
        <p class="default-prop"><b>source:</b> {{ log.source || "N/A" }}</p>

        <h3 class="mt-4 mb-2">Message</h3>
        <p class="message mb-4">{{ log.message || "No message" }}</p>

        <h3 class="mb-2">System Properties</h3>
        <div
          class="ml-4"
          v-for="(prop, i) in Object.keys(log.tags)"
          :key="'tag' + i"
        >
          <p class="default-prop">
            <b>{{ prop }}:</b> {{ log.tags[prop] || "N/A" }}
          </p>
        </div>

        <h3 class="mt-4 mb-2">Business Properties</h3>
        <div
          class="ml-4"
          v-for="(prop, i) in Object.keys(log.businessProperties)"
          :key="'prop' + i"
        >
          <p class="default-prop">
            <b>{{ prop }}:</b> {{ log.businessProperties[prop] || "N/A" }}
            <v-btn
              depressed
              x-small
              color="primary"
              v-if="!isIndexed(prop)"
              @click="addToIndex(prop)"
            >
              Add field to search
            </v-btn>
          </p>
        </div>
      </v-card-text>
      <v-divider></v-divider>
    </v-card>
  </v-dialog>
</template>
<script lang="ts">
import Vue from "vue";
import { Component } from "nuxt-property-decorator";
import { Prop } from "nuxt-property-decorator";
import { LogubLog } from "~/models/LogubLog";
import { LogLevel, logLevelColor } from "~/models/LogLevel";
import { formatDate } from "~/utils/helpers";
import { schema } from "~/utils/store-accessor";

@Component({
  name: "LogDetail",
})
export default class LogDetail extends Vue {
  @Prop({ required: true })
  private readonly log!: LogubLog;

  @Prop({ required: true, type: Boolean }) readonly value!: boolean;

  get dialog() {
    return this.value;
  }

  set dialog(v: boolean) {
    this.$emit("input", v);
  }

  get logLevelColor() {
    return (level: LogLevel) => `border-color: ${logLevelColor(level)}`;
  }

  get formattedDate() {
    return formatDate(this.log.timestamp, true);
  }

  isIndexed(value: string): boolean {
    return schema.businessProperties.includes(value);
  }

  addToIndex(value: string) {
    schema.addFieldToSchema(value);
  }
}
</script>
<style scoped>
.card-log {
  border-left: 5px solid white;
}

.default-prop {
  margin: 0;
}

.message {
  margin: 0;
  padding: 10px;
  background-color: #eee;
}
</style>
