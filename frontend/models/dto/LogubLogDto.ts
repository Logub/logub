import { SystemPropertiesDto } from '~/models/dto/SystemPropertiesDto';

export interface LogubLogDto {
  id: string;
  index: string;
  service?: string;
  systemProperties: SystemPropertiesDto;
  businessProperties: { [key: string]: string };
  message?: string;
  logger?: string;
  thread?: string;
  source?: string;
  timestamp: string;
  level: string;
}
