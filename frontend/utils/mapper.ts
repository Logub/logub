import { LogubLogDto } from '~/models/dto/LogubLogDto';
import { LogubLog } from '~/models/LogubLog';
import moment from 'moment';
import { LogLevel } from '~/models/LogLevel';

export class Mapper {

  static toDomain(dto: LogubLogDto): LogubLog {
    const { timestamp, level, systemProperties } = dto;
    return {
      ...dto,
      timestamp: moment(timestamp).toDate().getTime(),
      level: LogLevel[level as keyof typeof LogLevel],
      tags: systemProperties,
    };
  }
}
