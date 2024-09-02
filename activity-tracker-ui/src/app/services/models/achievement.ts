/* tslint:disable */
/* eslint-disable */
import { Activity } from '../models/activity';
export interface Achievement {
  achievementId?: number;
  activity?: Activity;
  createdAt?: string;
  info?: string;
  name?: string;
  picture?: Array<string>;
  type?: 'ONE_TIME' | 'DAILY' | 'AMOUNT';
  typeCheckmark?: string;
  updatedAt?: string;
}
