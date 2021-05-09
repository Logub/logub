import { SystemProperties } from '~/models/SystemProperties';
import { LogLevel } from '~/models/LogLevel';

export interface LogubLog {
  id: string;
  index: string;
  timestamp: number;
  level: LogLevel;
  tags: SystemProperties;
  businessProperties: { [key: string]: object };
  service?: string;
  message?: string;
  logger?: string;
  thread?: string;
  source?: string;
}


export function basicProperties(): Set<string>{
  return new Set(['service','message', 'logger', 'thread', 'source' ])
}
