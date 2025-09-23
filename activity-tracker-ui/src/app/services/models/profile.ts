/* tslint:disable */
/* eslint-disable */
import { Activity } from '../models/activity';
import { Event } from '../models/event';
import { File } from '../models/file';
import { User } from '../models/user';
export interface Profile {
  activities?: Array<Activity>;
  events?: Array<Event>;
  picture?: File;
  profileId?: number;
  user?: User;
  username?: string;
}
