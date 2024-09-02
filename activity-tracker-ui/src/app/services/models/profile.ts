/* tslint:disable */
/* eslint-disable */
import { Activity } from '../models/activity';
import { User } from '../models/user';
export interface Profile {
  activities?: Array<Activity>;
  groups?: {
[key: string]: Array<number>;
};
  profileId?: number;
  user?: User;
  username?: string;
}
