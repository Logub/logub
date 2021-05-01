import { LogubLogDto } from '~/models/dto/LogubLogDto';
import { LogubLog } from '~/models/LogubLog';
import moment from 'moment';
import { LogLevel } from '~/models/LogLevel';

export class Mapper {

  static toDomain(dto: LogubLogDto): LogubLog {
    const { timestamp, businessProperties, level, message, service, tags } = dto.event;
    return {
      id: dto.id,
      index: dto.index,
      timestamp: moment(timestamp).toDate().getTime(),
      level: LogLevel[level as keyof typeof LogLevel],
      message: message,
      tags: tags,
      service: service,
      businessProperties: businessProperties
    };
  }
}
