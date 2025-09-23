/* tslint:disable */
/* eslint-disable */
import { Activity } from '../models/activity';
import { File } from '../models/file';
export interface Achievement {
  achievementId?: number;
  activity?: Activity;
  createdAt?: string;
  finished?: boolean;
  info?: string;
  name?: string;
  picture?: File;
  type?: 'GOAL' | 'DAILY' | 'AMOUNT';
  typeSuperclass?: number;
  updatedAt?: string;
  xp?: number;
}
