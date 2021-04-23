interface LogubLog {
  id: string;
  index: string;
  timestamp: number;
  level: string;
  service: string;
  tags: SystemProperties;
  businessProperties: { [key: string]: object };
  message?: string;
}
