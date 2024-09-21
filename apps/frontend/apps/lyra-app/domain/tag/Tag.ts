import { z } from "zod";
import type { TagResponse } from "./TagResponse";

export enum TagColors {
  Default = "default",
  Purple = "purple",
  Pink = "pink",
  Red = "red",
  Blue = "blue",
  Yellow = "yellow",
}

export const colorClasses: Record<TagColors, string> = {
  [TagColors.Red]: "bg-red-100 text-red-800 border-red-200",
  [TagColors.Blue]: "bg-blue-100 text-blue-800 border-blue-200",
  [TagColors.Purple]: "bg-purple-100 text-purple-800 border-purple-200",
  [TagColors.Pink]: "bg-pink-100 text-pink-800 border-pink-200",
  [TagColors.Yellow]: "bg-yellow-100 text-yellow-800 border-yellow-200",
  [TagColors.Default]: "bg-gray-100 text-gray-800 border-gray-200",
};

export class Tag {
  id: string;
  name: string;
  color: TagColors;
  subscribers: ReadonlyArray<string> | string;
  createdAt?: Date | string;
  updatedAt?: Date | string;

  constructor({
    id,
    name,
    color = TagColors.Default,
    subscribers = [],
    createdAt,
    updatedAt,
  }: Partial<Tag> & { id: string; name: string }) {
    this.id = id;
    this.name = name;
    this.color = color;
    this.subscribers = subscribers;
    this.createdAt = this.normalizeDate(createdAt);
    this.updatedAt = this.normalizeDate(updatedAt);
  }

  static fromResponse(response: TagResponse): Tag {
    const color = Object.values(TagColors).includes(response.color as TagColors)
      ? response.color
      : TagColors.Default;
    const subscribers = Tag.normalizeSubscribers(response.subscribers);
    return new Tag({
      id: response.id,
      name: response.name,
      color: color as TagColors,
      subscribers:subscribers,
      createdAt: response.createdAt,
      updatedAt: response.updatedAt,
    });
  }

  get colorClass(): string {
    return colorClasses[this.color] || colorClasses[TagColors.Default];
  }

  get subscriberCount(): number {
    return this.subscribers.length;
  }
  private normalizeDate(date?: Date | string): Date | undefined {
    return typeof date === 'string' ? new Date(date) : date;
  }
  private static normalizeSubscribers(subscribers: ReadonlyArray<string> | string): ReadonlyArray<string> {
    return Array.isArray(subscribers) ? subscribers : (subscribers as string).split(',');
  }
}

export const tagSchema = z.object({
  id: z.string().min(1),
  name: z.string().min(2).max(50),
  color: z.nativeEnum(TagColors).default(TagColors.Default),
  subscribers: z.string()
});

