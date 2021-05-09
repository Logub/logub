import {LogLevel} from "~/models/LogLevel";

export enum FieldTypeDto {
  Tag,
  FullText,
  Geo,
  Numeric

}

export interface FieldSearchDto {
  type: FieldTypeDto;
  name?: string;
  values: string[];
  negation: boolean;
}

