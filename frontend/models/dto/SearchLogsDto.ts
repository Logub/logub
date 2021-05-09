import { FieldSearchDto } from '~/models/dto/FieldSearchDto';

export interface SearchLogsDto {
  texts?: Array<FieldSearchDto>;
  businessProperties: Array<FieldSearchDto>;
  systemProperties: Array<FieldSearchDto>;
  basicProperties: Array<FieldSearchDto>;
  sort: {
    field?: string;
    order?: 'ASC' | 'DESC'
  };
  beginAt?: number;
  endAt?: number;
  limit?: number;
  offset?: number;
}
