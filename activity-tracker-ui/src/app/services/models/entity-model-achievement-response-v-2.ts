/* tslint:disable */
/* eslint-disable */
import { Links } from '../models/links';
import { TypeSuperclass } from '../models/type-superclass';
export interface EntityModelAchievementResponseV2 {
  '_links'?: Links;
  biggestStreak?: number;
  currentStreak?: number;
  deadline?: string;
  finished?: boolean;
  info?: string;
  name?: string;
  type?: 'GOAL' | 'DAILY' | 'AMOUNT';
  typeData?: TypeSuperclass;
  unit?: string;
  xp?: number;
}
