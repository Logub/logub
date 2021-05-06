import { SearchLogsDto } from '~/models/dto/SearchLogsDto';
import { LogubLogDto } from '~/models/dto/LogubLogDto';
import { $axios } from '~/utils/store-accessor';

export class Api {

  private static readonly API_URL: string = process.env.API_URL || 'http://localhost:8080';

  static async searchLogs(searchDto: SearchLogsDto): Promise<LogubLogDto[]> {
    return $axios.$post<LogubLogDto[]>(`${this.API_URL}/logs/search`, searchDto);
  }
  static async getSchema(): Promise<string[]> {
    return $axios.$get<string[]>(`${this.API_URL}/logs/schema`);
  }
}
