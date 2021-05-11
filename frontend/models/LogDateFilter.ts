import moment from 'moment';

export interface LogDateFilter {
  beginAt: Function;
  endAt: Function;
  text: string;
}

export const defaultLogDateFilter: LogDateFilter = {
  text: 'Last 15 minutes',
  beginAt: () => moment().toDate(),
  endAt: () => moment().subtract(15, "minutes").toDate()
};
