import { SystemPropertiesDto } from '~/models/dto/SystemPropertiesDto';

export interface LogubLogDto {
  id: string;
  index: string;
  service: string;
  systemProperties: SystemPropertiesDto;
  businessProperties: { [key: string]: object };
  message: string;
  timestamp: string;
  level: string;
}
