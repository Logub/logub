export enum LogLevel {
  INFO = 'INFO',
  WARN = 'WARN',
  ERROR = 'ERROR',
  DEBUG = 'DEBUG'
}

export function logLevelColor(logLevel: LogLevel): string {
  switch (logLevel) {
    case LogLevel.INFO:
      return "#73BD15";
    case LogLevel.WARN:
      return "#E0A903";
    case LogLevel.ERROR:
      return "#C72000";
    case LogLevel.DEBUG:
      return "#A896C7";
    default:
      return "#FFF";
  }
}
