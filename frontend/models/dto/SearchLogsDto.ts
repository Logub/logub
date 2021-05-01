import { SystemPropertiesDto } from '~/models/dto/SystemPropertiesDto';

export interface SearchLogsDto {
  businessProperties: { [key: string]: object };
  tags: SystemPropertiesDto;
  sort: {
    field?: string;
    order?: 'ASC' | 'DESC'
  };
  beginAt?: number;
  endAt?: number;
  limit?: number;
  offset?: number;
}