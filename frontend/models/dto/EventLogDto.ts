import { SystemPropertiesDto } from '~/models/dto/SystemPropertiesDto';

export interface EventLogDto {
  service: string;
  tags: SystemPropertiesDto;
  businessProperties: { [key: string]: object };
  message: string;
  timestamp: string;
  level: string;
}
