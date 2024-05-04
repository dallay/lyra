import type Command from './Command';

export default interface CommandBus {
	dispatch(command: Command): Promise<void>;
}
