/* tslint:disable */
/* eslint-disable */
import { Profile } from '../models/profile';
export interface Event {
  end?: string;
  eventId?: number;
  linkId?: number;
  name?: string;
  profile?: Profile;
  start?: string;
  type?: 'SESSION' | 'ACHIEVEMENT_DONE' | 'PLAN';
}
