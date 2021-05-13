import {SearchLogsDto} from '~/models/dto/SearchLogsDto';
import {LogubLogDto} from '~/models/dto/LogubLogDto';
import {$axios} from '~/utils/store-accessor';
import {SchemaFieldDto} from "~/models/dto/SchemaFieldDto";
import {FieldTypeDto} from "~/models/dto/FieldSearchDto";

export class Api {

  private static readonly API_URL: string = process.env.API_URL || 'http://localhost:8080';
  private static readonly API_URL_DEMO: string = process.env.API_URL || 'http://localhost:8081';

  static async searchLogs(searchDto: SearchLogsDto): Promise<LogubLogDto[]> {
    return $axios.$post<LogubLogDto[]>(`${this.API_URL}/logs/search`, searchDto);
  }

  static async getSchema(): Promise<string[]> {
    return $axios.$get<string[]>(`${this.API_URL}/logs/schema`);
  }

  static async addField(businessProperties: string) {
    const schemaField: SchemaFieldDto = {
      type: FieldTypeDto.Tag,
      name: businessProperties
    };
    return $axios.$post<void>(`${this.API_URL}/logs/schema`, schemaField);
  }

  static async getUsers() {
    return $axios.$get<any[]>(`${this.API_URL_DEMO}/users`);
  }

  static async addUser(user :any) {
    return $axios.$post<void>(`${this.API_URL_DEMO}/users`, user);
  }

  static async deleteUser(userId: number) {
    return $axios.$delete<void>(`${this.API_URL_DEMO}/users/${userId}`);
  }
  static async postCustomLog(log :any) {
    return $axios.$post<void>(`${this.API_URL_DEMO}/logs`, log);
  }
}
