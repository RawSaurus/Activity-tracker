/* tslint:disable */
/* eslint-disable */
import { Achievement } from '../models/achievement';
import { File } from '../models/file';
import { Profile } from '../models/profile';
export interface Template {
  achievements?: Array<Achievement>;
  category?: 'ACTIVITY' | 'SPORT';
  createdAt?: string;
  creator?: string;
  downloads?: number;
  info?: string;
  name?: string;
  picture?: File;
  profile?: Profile;
  rating?: number;
  templateId?: number;
  type?: string;
  updatedAt?: string;
}
