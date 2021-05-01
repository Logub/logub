import { EventLogDto } from '~/models/dto/EventLogDto';

export interface LogubLogDto {
  id: string;
  index: string;
  event: EventLogDto;
}
