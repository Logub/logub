import { FieldSearchDto } from '~/models/dto/FieldSearchDto';

export interface SearchLogsDto {
  texts?: Array<FieldSearchDto>;
  businessProperties: Array<FieldSearchDto>;
  systemProperties: Array<FieldSearchDto>;
  basicProperties: Array<FieldSearchDto>;
  levels: Array<FieldSearchDto>;
  sort: SortLogsDto;
  beginAt?: number;
  endAt?: number;
  limit?: number;
  offset?: number;
}

export interface SortLogsDto {
  field?: string;
  order?: 'ASC' | 'DESC'
}
