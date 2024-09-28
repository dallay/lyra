import type Command from './Command';

export default interface CommandHandler<T extends Command> {
  handle(command: T): Promise<void>;
}
