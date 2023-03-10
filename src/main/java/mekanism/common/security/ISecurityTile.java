package mekanism.common.security;

import mekanism.api.EnumColor;
import mekanism.common.tile.component.TileComponentSecurity;
import mekanism.common.util.LangUtils;

public interface ISecurityTile {
    public TileComponentSecurity getSecurity();

    public enum SecurityMode {
        PUBLIC("security.public", EnumColor.BRIGHT_GREEN),
        PRIVATE("security.private", EnumColor.RED),
        TRUSTED("security.trusted", EnumColor.ORANGE);

        private String display;
        private EnumColor color;

        public String getDisplay() {
            return color + LangUtils.localize(display);
        }

        private SecurityMode(String s, EnumColor c) {
            display = s;
            color = c;
        }
    }
}
