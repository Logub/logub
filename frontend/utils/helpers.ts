
import {Moment} from "moment-timezone";
import moment from "moment-timezone";
export const sleep = async (ms: number): Promise<void> => new Promise(res => setTimeout(res, ms));

export function formatDate(dateNumber: number, detail: boolean = false): string {
  const date: Moment = moment(dateNumber);
  return date.format(detail ? 'MMM DD, YYYY - HH:mm:ss.SSS' : 'MMM DD HH:mm:ss.SSS');
}
