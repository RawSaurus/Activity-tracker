/* tslint:disable */
/* eslint-disable */
import { Achievement } from '../models/achievement';
import { Comment } from '../models/comment';
import { File } from '../models/file';
import { Profile } from '../models/profile';
import { Session } from '../models/session';
export interface Activity {
  achievements?: Array<Achievement>;
  activityId?: number;
  category?: 'ACTIVITY' | 'SPORT';
  comments?: Array<Comment>;
  createdAt?: string;
  creator?: string;
  info?: string;
  name?: string;
  picture?: File;
  private?: boolean;
  profile?: Profile;
  sessions?: Array<Session>;
  type?: string;
  updatedAt?: string;
}
