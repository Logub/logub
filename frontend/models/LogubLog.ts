import { SystemProperties } from '~/models/SystemProperties';
import { LogLevel } from '~/models/LogLevel';

export interface LogubLog {
  id: string;
  index: string;
  timestamp: number;
  level: LogLevel;
  service: string;
  tags: SystemProperties;
  businessProperties: { [key: string]: object };
  message?: string;
}
