/* tslint:disable */
/* eslint-disable */
import { Achievement } from '../models/achievement';
import { Comment } from '../models/comment';
import { Profile } from '../models/profile';
import { Session } from '../models/session';
export interface Activity {
  achievements?: Array<Achievement>;
  activityId?: number;
  category?: 'ACTIVITY' | 'SPORT';
  comments?: Array<Comment>;
  createdAt?: string;
  creator?: string;
  creatorId?: number;
  downloads?: number;
  info?: string;
  name?: string;
  original?: boolean;
  originalActivity?: number;
  picture?: Array<string>;
  private?: boolean;
  profile?: Profile;
  rating?: number;
  sessions?: Array<Session>;
  type?: string;
  updatedAt?: string;
}
